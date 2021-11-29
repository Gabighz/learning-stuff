// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

// The idea behind this factory is so that:
// - we have a single instance of it
// - managers, not us, pay for the deployment of new Campaigns, and
// - not compromise the security of our dApp by having managers directly deploy Campaigns
//   and possibly modify the source code beforehand
contract CampaignFactory {
    address[] public deployedCampaigns;

    function createCampaign(uint minimum) public {
        address newCampaign = address(new Campaign(minimum, msg.sender));
        deployedCampaigns.push(newCampaign);
    }

    function getDeployedCampaigns() public view returns (address[] memory) {
        return deployedCampaigns;
    }
}

contract Campaign {
    struct Request {
        string description;
        uint value;
        address payable recipient;
        bool complete;
        uint approvalCount;
        mapping(address => bool) approvals;
    }
    // imo a 'Request[] requests' would've been cleaner, but on this version of Solidity
    // we're getting "struct containing a (nested) mapping cannot be constructed"
    // when trying to initialize a variable of type Request with approvalCount as a field
    uint public numRequests;
    mapping(uint => Request) public requests;

    address public manager;
    uint public minimumContribution;

    // mappings in Solidity are good only for look-ups
    // (keys are not stored and values are not iterable)
    // and insertions ('all values exist' attitude where we get a default value
    // if it didn't exist in the mapping beforehand)
    // we use a mapping instead of an array because of scalability issues
    // (gas costs would get really high if we were to iterate through e.g. 10,000 contributors)
    uint public contributorsCount;
    mapping(address => bool) public contributors;

    modifier restricted() {
        require(msg.sender == manager);
        _;
    }

    constructor(uint minimum, address _manager) {
        // the manager of the fund-raising compaign
        // we can't use msg.sender here because that would be the address of the factory
        manager = _manager;
        minimumContribution = minimum;
    }

    function contribute() public payable {
        require(msg.value >= minimumContribution);

        contributors[msg.sender] = true;
        contributorsCount++;
    }

    // in this context, 'memory' tells us that it's passed by value / deep copy
    // 'storage' would make it be passed by reference / shallow copy
    function createRequest(string memory description, uint value, address payable recipient) public restricted {
        Request storage newRequest = requests[numRequests];
        newRequest.description = description;
        newRequest.value = value;
        newRequest.recipient = recipient;
        newRequest.complete = false;
        newRequest.approvalCount = 0;
        numRequests++;
    }

    function approveRequest(uint index) public {
        Request storage request = requests[index];

        require(contributors[msg.sender]);
        require(!request.approvals[msg.sender]); // if already voted, should be rejected

        request.approvals[msg.sender] = true;
        request.approvalCount++;
    }

    function finalizeRequest(uint index) public restricted {
        Request storage request = requests[index];

        require(!request.complete);
        require(request.approvalCount > (contributorsCount / 2));

        request.recipient.transfer(request.value);

        request.complete = true;
    }

    function getSummary() public view returns (uint, uint, uint, uint, address) {
        return (
            minimumContribution,
            address(this).balance,
            numRequests,
            contributorsCount,
            manager
        );
    }
}