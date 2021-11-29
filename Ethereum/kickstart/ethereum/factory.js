import web3 from './web3'
import CampaignFactory from './build/CampaignFactory.json'

const instance = new web3.eth.Contract(
    CampaignFactory.abi,
    '0xc90663B7BE5E7f01D516c36e50cD12825bAf73A3'
)

export default instance