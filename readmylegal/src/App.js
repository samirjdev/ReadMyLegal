import logo from './resources/AI_logo_darkmode.png';
import './App.css';

function fetchData()
{
  var body = document.getElementById("textarea");
  var bodyValue = body.value;
  var password = document.getElementById("password");
  var passwordValue = password.value;
  console.log("DATA FETCHED!");
  fetch('http://localhost:5252/prompt/json',  {
    method: "POST",
    mode: 'no-cors',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      body: bodyValue,
      password: passwordValue
    })
  })
    .then(response => {
      if (!response.ok)
      {
        throw new Error('Network response was not okay!');
      }
      return response.json();
    })
    .then(data => {
      document.getElementById('textarea').value = data.result;
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

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
      <div className="main-content">
        <div className="left-screen">
          <div className="password-container">
          <form>
            <label className="password-label" htmlFor="password">Password: </label>
            <input type="text" id="password" name="password" className="password-input" placeholder="Enter your pasword" required></input>
          </form>
          </div>
        </div>
        <div className="right-screen">
          <p className="textbox-container">
              <textarea id="textarea" className="textarea" rows="4" cols="60" placeholder="Fetch Data Displays Here"></textarea>
              <button onClick={fetchData}>Fetch Some Data</button>
          </p>
        </div>
      </div>
    </div>
  );
}


export default App;
