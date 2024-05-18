import MUICard from '../mui/components/MUICard'

const Offers = () => {
    return (
        <div className="title">
            <h1>Top Offers</h1>
            <MUICard
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
