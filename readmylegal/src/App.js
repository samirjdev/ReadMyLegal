import logo from './resources/AI_logo_darkmode.png';
import './App.css';
import { useState } from 'react';

var hasFile = false;
function fetchData()
{
  if (hasFile)
  {
    var body = document.getElementById("myfile");
    console.log("Using File");
  }
  else
  {
    var body = document.getElementById("textarea");
    console.log("Using Text");
  }
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
      password: passwordValue,
      type: "document"
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
      document.getElementById('outputarea').value = data.response;
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

function OnDrop()
{
  const textarea = document.getElementById("textarea");
  textarea.readOnly = true;
}

function App() {
  const onUpload = (files) =>
  {
    console.log(files);
  };

  document.addEventListener('DOMContentLoaded', function () {
    const fileInput = document.getElementById('myfile');
    function handleFileSelection(event) {
      const selectedFile = event.target.files[0];
      console.log("CHECKPOINT")
      if (selectedFile) {
        OnDrop();
      }
    }
    if(fileInput)
    {
      fileInput.addEventListener('change', handleFileSelection);
    }
  });

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
          <p className="textbox-container">
            <textarea id="textarea" className="textarea" rows="4" cols="60" placeholder="Data to Send"></textarea>
          </p>
          <input type="file" id="myfile" className="myfile" onClick={OnDrop}/>
        <button className="submit" onClick={fetchData}>Fetch Some Data</button>
        </div>
      </div>
      <div className="low-content">
        <p className="textbox-container">
            <textarea id="outputarea" className="textarea" rows="4" cols="60" placeholder="Fetch Data Displays Here" readOnly></textarea>
        </p>
      </div>
    </div>
  );
}


export default App;
