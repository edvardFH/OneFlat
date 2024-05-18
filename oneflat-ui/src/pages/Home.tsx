import Hero from '@/components/hero/hero'
import Layout from '../layouts/layout'
import HeroBanner from '@/components/heroBanner/heroBanner'

const Home = () => {
    return (
        <Layout showHeader={true}>
            <div className="home">
                <Hero />
                <HeroBanner />
            </div>
        </Layout>
    )
}

export default Home
