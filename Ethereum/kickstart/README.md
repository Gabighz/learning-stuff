To deploy a CampaignFactory:
1. Have Metamask installed
2. Configure a test/mainnet endpoint on Infura
3. Pass them into an .env file in the 'ethereum' folder
4. Run the following inside 'ethereum':
    npm install
    node compile.js
    node deploy.js

Then to run the next.js server:
    npm run dev