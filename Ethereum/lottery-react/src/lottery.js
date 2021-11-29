import web3 from './web3'

// https://rinkeby.etherscan.io/address/0x8266Ae5a5A12F5F4b4e3D620506F5c4C1Bb0AA33
const address = '0x8266Ae5a5A12F5F4b4e3D620506F5c4C1Bb0AA33'

const abi = [
    {
        "inputs":[],
        "stateMutability":"nonpayable",
        "type":"constructor"
    },
    {
        "inputs":[],
        "name":"enter",
        "outputs":[],
        "stateMutability":"payable",
        "type":"function"
    },
    {
        "inputs":[],
        "name":"getPlayers",
        "outputs":[{"internalType":"address payable[]","name":"","type":"address[]"}],
        "stateMutability":"view",
        "type":"function"
    },
    {
        "inputs":[],
        "name":"manager",
        "outputs":[{"internalType":"address","name":"","type":"address"}],
        "stateMutability":"view",
        "type":"function"
    },
    {
        "inputs":[],
        "name":"pickWinner",
        "outputs":[],
        "stateMutability":"nonpayable",
        "type":"function"
    },
    {
        "inputs":[{"internalType":"uint256","name":"","type":"uint256"}],
        "name":"players",
        "outputs":[{"internalType":"address payable","name":"","type":"address"}],
        "stateMutability":"view",
        "type":"function"
    },
    {
        "stateMutability":"payable",
        "type":"receive"
    }
]

// creates a 'pointer' to our deployed contract
// and the abi serves as a representation of what's going on / how we can interact with
// our deployed contract
export default new web3.eth.Contract(abi, address)