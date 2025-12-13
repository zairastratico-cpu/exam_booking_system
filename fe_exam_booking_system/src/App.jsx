import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";

import {BrowserRouter, Routes, Route, Navigate} from "react-router-dom";

import API_URL from "./config/api";

function App() {
  const [count, setCount] = useState(0);

  return (
    <BrowserRouter>
    <Routes>
      <Route></Route>
    </Routes>
    </BrowserRouter>
  );
}

export default App;
