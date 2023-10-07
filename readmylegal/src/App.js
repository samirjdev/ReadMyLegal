import logo from './resources/AI_logo_darkmode.png';
import './App.css';

function App() {
  return (
    <div className="App">
      <div id="topHeader">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Read My Legal
          </p>
        </header>
      </div>
      <body>
        <div class="textbox-container">
          <input type="text" class="textbox" placeholder="Enter stuff here"></input>
        </div>
      </body>
    </div>
  );
}

export default App;
