/*
    With this script, we assume that the whoever visits our page has Metamask installed.
    This pretty much 'highjacks' the Provider of that wallet so that we can use the web3
    version of our app with their accounts.
*/

import Web3 from "web3";
 
window.ethereum.request({ method: "eth_requestAccounts" });
 
const web3 = new Web3(window.ethereum);
 
export default web3;