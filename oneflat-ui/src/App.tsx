import { Route,  BrowserRouter, Routes } from 'react-router-dom';
import Home from './pages/Home';
import '@/App.css'
import SignUp from './pages/SignUp';


const App = () => {

  return (
    <BrowserRouter>
      <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/signup" element={<SignUp />} />
      </Routes>
    </BrowserRouter>
  );
};


export default App;