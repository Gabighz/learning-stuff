const assert = require('assert')
const ganache = require('ganache-cli')
const Web3 = require('web3')
const web3 = new Web3(ganache.provider({ gasLimit: '10000000' }))

const compiledFactory = require('../ethereum/build/CampaignFactory.json')
const compiledCampaign = require('../ethereum/build/Campaign.json')

let accounts
let factory
let campaignAddress
let campaign
const GAS = '10000000'

beforeEach(async () => {
    accounts = await web3.eth.getAccounts()

    factory = await new web3.eth.Contract(compiledFactory.abi)
        .deploy({ data: compiledFactory.evm.bytecode.object })
        .send({ from: accounts[0], gas: GAS });

    await factory.methods.createCampaign('100').send({ 
        from: accounts[0],
        gas: GAS
    });

    [campaignAddress] = await factory.methods.getDeployedCampaigns().call()
    campaign = await new web3.eth.Contract(
        compiledCampaign.abi,
        campaignAddress
    );
})

describe('Campaigns', () => {
    it('deploys a factory and a campaign', () => {
        assert.ok(factory.options.address)
        assert.ok(campaign.options.address)
    })

    it('marks caller as campaign manager', async () => {
        const manager = await campaign.methods.manager().call()
        assert.equal(accounts[0], manager)
    })

    it('allows people to contribute money and marks them as contributors', async () => {
        await campaign.methods.contribute().send({
            value: '200',
            from: accounts[1]
        })

        const isContributor = await campaign.methods.contributors(accounts[1]).call()
        assert(isContributor)
    })

    it('requires a minimum contribution', async () => {
        try {
            await campaign.methods.contribute().send({
                value: '5',
                from: accounts[1]
            })
            assert(false)
        } catch (err) {
            assert(err)
        }
    })

    it('allows a manager to make a payment request', async () => {
        await campaign.methods
            .createRequest('Buy batteries', '100', accounts[1])
            .send({
                from: accounts[0],
                gas: GAS
            })

        const request = await campaign.methods.requests(0).call()
        assert.equal('Buy batteries', request.description)
    })

    it('processes requests', async () => {
        await campaign.methods.contribute().send({
            from: accounts[0],
            value: web3.utils.toWei('10', 'ether')
        })

        await campaign.methods
            .createRequest('A', web3.utils.toWei('5', 'ether'), accounts[1])
            .send({ from: accounts[0], gas: GAS })

        await campaign.methods.approveRequest(0).send({
            from: accounts[0],
            gas: GAS
        })

        await campaign.methods.finalizeRequest(0).send({
            from: accounts[0],
            gas: GAS
        })

        let balance = await web3.eth.getBalance(accounts[1])
        balance = web3.utils.fromWei(balance, 'ether')
        balance = parseFloat(balance)

        // Each account starts with 100 ether, then accounts[1] pays for gas in prev test
        // and then in test we send it 5 ether, so their balance should be almost 105 ether.
        // This is a limitation with ganache because, as of now and as far as I know, we cannot
        // reset balances.
        assert(balance > 104)
    })
})