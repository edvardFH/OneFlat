import _Card from '../mui/components/Card'

const Offers = () => {
    //     let [bestOffers, setBestOffers] = useState(null)

    //     // 3. Create out useEffect function
    //   useEffect(() => {
    //     fetch("https://dog.ceo/api/breeds/image/random/3")
    //     .then(response => response.json())
    //         // 4. Setting *dogImage* to the image url that we received from the response above
    //     .then(data => setDogImage(data.message))
    //   },[])
    return (
        <div className="title">
            <h1>Top Offers</h1>
            <_Card
                height={140}
                src={
                    'https://etudestech.com/wp-content/uploads/2023/05/midjourney-scaled.jpeg'
                }
                data={{ title: 'test', desc: 'qdqds' }}
            />
        </div>
    )
}

export default Offers
