import { Footer } from '@/components/footer/footer'
import Header from '@/components/header/header'
import { ReactNode } from 'react'

interface Props {
    children: ReactNode
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
