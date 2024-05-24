import { Link } from 'react-router-dom'

const Header = () => {
    return (
        <div className="header">
            <div className="brand-nav">
                <span>One Flat</span>
            </div>
            <div className="nav-links">
                <div className="nav-items-auth">
                    <Link to={'/signup'}>Sign up</Link>
                    <Link to={'/login'}>Log in</Link>
                </div>
            </div>
        </div>
    )
}
export default Header
