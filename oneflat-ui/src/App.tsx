import { Route, BrowserRouter, Routes } from 'react-router-dom'
import Home from './pages/Home'
import './App.css'
import SignUp from './pages/SignUp'
import Dashboard from './pages/Dashboard'
import Accommodations from './pages/Accommodations'
import Login from './pages/Login'

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/login" element={<Login />} />
                <Route path="/dashboard" element={<Dashboard />} />
                <Route path="/accommodations" element={<Accommodations />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App
