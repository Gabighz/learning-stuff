This has been done as part of the [Ethereum and Solidity: The Complete Developer's Guide](https://www.udemy.com/course/ethereum-and-solidity-the-complete-developers-guide/) course on Udemy, taught by Stephen Grinder.

Inbox, lottery, and lottery-react are simple, toy projects (still found them super cool and fun).

**Kickstart** is a production-ready dApp and could be deployed in the wild. It's a demonstration of a possibly more elegant solution than a centralized app for Kickstarter's issues with scammers and failed projects. 

We leverage the nature of smart contracts to control how the 'idea person'/manager can use the raised funds. The funds are sent to an Ethereum contract. This manager can create spending requests that attempt to send money to some external addresses (ideally vendors) and the contributors vote on whether to approve it.

Of course, it's not a perfect solution, as it assumes that the contributors are going to vote, that the vendor is not malicious, and that the address is legitimate - possibly other drawbacks too.
