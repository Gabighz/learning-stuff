const HDWalletProvider = require('@truffle/hdwallet-provider');
const Web3 = require('web3');
const { interface, bytecode } = require('./compile');

const provider = new HDWalletProvider(
    'cannon kid various leisure kidney fold hood bachelor neck sport trip mystery',
    'https://rinkeby.infura.io/v3/fd00b234584d4a80a3dfaff5a29f11f5'
);

const web3 = new Web3(provider);

const deploy = async () => {
    const accounts = await web3.eth.getAccounts();
    
    console.log('Attempting to deploy from account ', accounts[0]);

    const result = await new web3.eth.Contract(JSON.parse(interface))
        .deploy({ data: bytecode, arguments: ['Hello world'] })
        .send({ gas: '1000000', from: accounts[0] });

    console.log('Contract deployed to:', result.options.address);
    provider.engine.stop();

};
deploy();

// Deployed @ https://rinkeby.etherscan.io/address/0xF1D72Dd25F5304f01F9561d1F8C0ec4FD04696d3
// Tx with setMessage('asd'), using Remix IDE: https://rinkeby.etherscan.io/tx/0x715203bce8e52a4fba757bdebb731297295bf55fdd4001db35a803728285e0e9