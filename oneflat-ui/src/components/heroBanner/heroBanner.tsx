import Offers from '../topOffers/offers'
const HeroBanner = () => {
    return (
        <div className="hero">
            <div className="hero-title">
                <h1>Top Offers</h1>
            </div>
            <div>
                <Offers />
            </div>
        </div>
    )
}

export default HeroBanner
