import { IAccomodations } from '@/components/topOffers/offers'

interface Props {
    src: string
    alt?: string
    informations: IAccomodations
}

const _Card = ({ src, alt, informations }: Props) => {
    return (
        <div className="box">
            <div className="top">
                <img src={src} alt={alt} />
                <span>
                    <i className="fas fa-heart"></i>
                    <i className="fas fa-exchange-alt"></i>
                </span>
            </div>
            <div className="bottom">
                <h3>{informations.type}</h3>
                <p>{informations.description}</p>
                <div className="advants">
                    <div>
                        <span>Bedrooms</span>
                        <div>
                            <i className="fas fa-th-large"></i>
                            <span>{informations.bedrooms}</span>
                        </div>
                    </div>
                    <div>
                        <span>Bathrooms</span>
                        <div>
                            <i className="fas fa-shower"></i>
                            <span>{informations.bathrooms}</span>
                        </div>
                    </div>
                    <div>
                        <span>Area</span>
                        <div>
                            <i className="fas fa-vector-square"></i>
                            <span>
                                {informations.area}
                                <span>m²</span>
                            </span>
                        </div>
                    </div>
                </div>
                <div className="price">
                    <span>For Sale</span>
                    <span>{informations.price} $</span>
                </div>
            </div>
        </div>
    )
}

export default _Card
