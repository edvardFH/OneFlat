import { Footer } from '@/components/footer/footer'
import Header from '@/components/header/header'

interface Props {
    children: JSX.Element
    showHeader?: boolean
    showFooter?: boolean
}

const Layout = ({ children, showHeader = true, showFooter = true }: Props) => {
    return (
        <div>
            {showHeader && <Header />}
            <main>{children}</main>
            {showFooter && <Footer />}
        </div>
    )
}

export default Layout
