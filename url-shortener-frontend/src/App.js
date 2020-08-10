import React from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import logo from './logo.svg';
import './App.css';
import ShortURLApp from "./ShortURLApp";
import RedirectToURL from "./RedirectToURL";

function App() {
  return (
      <Router>
          <div className="App">
              <Route path="/create" component={ShortURLApp} />
              <Route path="/r/:slug" component={RedirectToURL} />
          </div>
      </Router>
  );
}

export default App;
