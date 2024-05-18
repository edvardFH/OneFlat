import Hero from '@/components/hero/hero'
import HeroBanner from '@/components/heroBanner/heroBanner'
import Layout from '@/layouts/layout'

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
