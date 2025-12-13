import { useState } from "react";
import "./App.css";

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

// Components
import BookNavbar from "./components/BookNavbar";
import BookFooter from "./components/BookFooter";

// Pages
import Homepage from "./pages/public/Homepage";
import API_URL from "./config/api";

function App() {
  const [count, setCount] = useState(0);

  return (
    <BrowserRouter>
      <BookNavbar />
      <Routes>
        <Route>
          <Route path="/" element={<Homepage />} />
        </Route>
      </Routes>
      <BookFooter />
    </BrowserRouter>
  );
}

export default App;
