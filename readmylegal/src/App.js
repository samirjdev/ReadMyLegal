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
    mode: 'cors',
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
      document.getElementById('outputarea').value = data.data;
    })
    .catch(error => {
      console.error('Error:', error);
    });

  //getData();
}
/*
function getData()
{
  fetch('http://localhost:5252/prompt/json',  {
    method: "GET",
    mode: 'cors',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  })
    .then(response => {
      if (!response.ok)
      {
        throw new Error('Network response was not okay!');
      }
      return response.json();
    })
    .then(data => {
      console.log(data);
    })
    .catch(error => {
      console.error('Error:', error);
    });
}
*/
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
        </div>
        <div className="right-screen">
          <div className="password-container">
            <form>
              <label className="password-label" htmlFor="password">Password: </label>
              <input type="text" id="password" name="password" className="password-input" placeholder="Enter your pasword" required></input>
            </form>
          </div>
          <p className="textbox-container">
            <textarea id="textarea" className="textarea" rows="4" cols="60" placeholder="Data to Send"></textarea>
            <button className="submit" onClick={fetchData}>Fetch Some Data</button>
          </p>
          <p className="textbox-container">
            <textarea id="outputarea" className="textarea" rows="4" cols="60" placeholder="Fetch Data Displays Here" readOnly></textarea>
          </p>
        </div>
      </div>
    </div>
  );
}


export default App;
