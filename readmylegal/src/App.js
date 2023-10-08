import logo from './resources/AI_logo_darkmode.png';
import './App.css';
import loader from "./resources/loading.gif";
import { useState } from 'react';

var requestType = 'document';
var hasFile = false;
var bodyValue;
function fetchData()
{
  document.getElementById('load-container').style.display = 'flex';
  if (!hasFile)
  {
    var body = document.getElementById("textarea");
    bodyValue = body.value;
    console.log("Using Text");
  }
  console.log(bodyValue + "2");
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
      password: passwordValue,
      type: requestType
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
      document.getElementById('load-container').style.display = 'none';
      document.getElementById('output').innerHTML = data.response;
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

function OnTypeSelect()
{
  var radios = document.getElementsByName('request-type');
  var val= "";
  for (var i = 0, length = radios.length; i < length; i++) {
      if (radios[i].checked) {
         val = radios[i].value; 
         break;
       }
  }
  
  requestType = val;
  console.log(val);
}

function OnDrop()
{
  const textarea = document.getElementById("textarea");
  //textarea.readOnly = true;
  const fileInput = document.getElementById('myfile'); // Your file input element
  fileInput.addEventListener('change', async (event) => {
    const selectedFile = event.target.files[0];
    if (selectedFile) {
      try {
        hasFile = true;
        const fileContent = await readFile(selectedFile);
        bodyValue = fileContent;
        textarea.value = bodyValue;
        console.log(bodyValue + "1");
      } catch (error) {
        console.error('Error reading the file:', error);
      }
    }
  });
}

function readFile(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();

    reader.onload = (event) => {
      resolve(event.target.result);
    };

    reader.onerror = (error) => {
      reject(error);
    };

    reader.readAsText(file);
  });
}

function App() {
  const onUpload = (files) =>
  {
    console.log(files);
  };

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
          <p className="leftpar">
            Introducing our GPT 3.5 powered Legal Document Reader, your key to effortless understanding of your rights, responsibilites, and crucial information in legal documents.
            Our advanced AI technology simplifies complex legal language, providing you with clarity, efficiency, and customized insights tailored towards your needs. Know
            your rights and take control of your information. Visit our cutting-edge legal documents analyzers with a PDF files or text to find out today.
          </p>
        </div>
        <div className="right-screen">
          <div className="password-container">
            <form>
              <label className="password-label" htmlFor="password">Password: </label>
              <input type="text" id="password" name="password" className="password-input" placeholder="Enter your pasword" required></input>
            </form>
          </div>
          <input type="file" id="myfile" className="myfile" onClick={OnDrop}/>
          <input type="radio" id="typelol1" className="typelol" name="request-type" value="document" onClick={OnTypeSelect}/><t className="typelol">Legal document</t>
          <input type="radio" id="typelol2" className="typelol" name="request-type" value="transcript" onClick={OnTypeSelect}/><t className="typelol">Audio Recording</t>
          <p className="textbox-container">
            <textarea id="textarea" className="textarea" rows="4" cols="60" placeholder="Data to Send"></textarea>
          </p>
        <button className="submit" onClick={fetchData}>Analyze</button>
        </div>
      </div>
      <div className="low-content">
        <div id="load-container">
          <img src={loader} id="load" alt="Loading..." />
        </div>
        <p id="output" className="textbox-container">
        </p>
      </div>
    </div>
  );
}


export default App;
