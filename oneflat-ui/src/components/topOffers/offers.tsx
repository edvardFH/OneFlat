import { useState, useEffect } from 'react'
import _Card from '../mui/components/Card'

export interface IAccomodations {
    type: string
    location: string
    price: number
    bedrooms: number
    bathrooms: number
    area: number
    description: string
}

const Offers = () => {
    const [bestOffers, setBestOffers] = useState<IAccomodations[]>([])

    useEffect(() => {
        fetch('http://localhost:8000/accommodations')
            .then((response) => response.json())
            .then((data) => {
                if (Array.isArray(data)) {
                    setBestOffers(getRandomOffers(data, 3))
                } else {
                    console.error('Invalid data format:', data)
                }
            })
            .catch((error) => console.error('Error fetching data:', error))
    }, [])

    function getRandomOffers(
        arr: IAccomodations[],
        count: number
    ): IAccomodations[] {
        const shuffled = arr.sort(() => 0.5 - Math.random())
        return shuffled.slice(0, count)
    }

    return (
        <div className="container">
            {bestOffers.map((offer, index) => (
                <_Card
                    key={index}
                    src="https://etudestech.com/wp-content/uploads/2023/05/midjourney-scaled.jpeg"
                    informations={{
                        type: offer.type,
                        location: offer.location,
                        description: offer.location,
                        bathrooms: offer.bathrooms,
                        bedrooms: offer.bedrooms,
                        area: offer.area,
                        price: offer.price,
                    }}
                />
            ))}
        </div>
    )
}

export default Offers
