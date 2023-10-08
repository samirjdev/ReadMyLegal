![Logo](/resources/AI_Full_Spinning_White.gif)
# This biz is *serious*.
## Table of Contents
- [You deserve to know your rights.](#you-deserve-to-know-your-rights)
- [Reading your legal biz.](#reading-your-legal-biz)
- [We built it with what?](#we-built-it-with-what)
- [Knowing with style.](#knowing-with-style)
- [The challenges? How much?](#the-challenges-how-much)
- [And the result...?](#and-the-result)
- [What's next for ReadMyLegal.](#whats-next-for-readmylegal)
- [Let's set this up!](#lets-set-this-up)
- [License Information](#license-information)

## You deserve to know your rights.

According to the *World Justice Project (WJP)*, the decline in the rule of law currently "represents almost **85%** of the world's population", affecting our individual legal awareness. With the decline of legal awareness being at an all-time high, marks the birth of our inspiration that brought our team together to tackle an underrepresented challenge that affects our global society. 

Another shared interest between our team is emerging technologies. With the exponential growth of the applications of artificial intelligence, our team found the opportunity to apply our existing AI knowledge and use it to addressing the worldwide decline of legal awareness.

## Reading your legal biz.

ReadMyLegal.biz is a state-of-the-art GPT-3.5 Turbo powered legal document reader, designed to simplify your legal interactions and empower you with a comprehensive understanding of your rights and responsibilities, as well as those of the other.

Developed as part of the Knighthacks 2023 in Orlando, FL, ReadMyLegal.biz is designed to provide individuals an understanding of their rights and responsibilities. Navigating the intricacies of legal documents can be a daunting task, but with our advanced AI technology, you can effortlessly decode complex legal jargon and gain clarity on critical information by uploading a PDF document, pasting text into a box, or through audio transcription for accessible options*.

## We built it with what?

ReadMyLegal.biz was built from the ground up as a web app in mind, making it accessible from almost every modern platform that has access to the internet. Utilizing Java as the backend and JavaScript as the front end, integrating OpenAI's GPT model was a simple task. The connection between the backend and the frontend was solved by creating a REST API to handle the task of processing complex legal documents and generating detailed responses.

## Functional style.

The website has to be functional, easy-to-use, and stylish. We *spiced* it up with the latter. Leveraging React, we designed a precisely crafted interface that is not only extremely stylish but easy-to-use for everyone as well, including accessibility options.

## The challenges? How much?

Alot. We ran into **alot**. But each and every time, we came back stronger (*and smarter*) than before and, with a bit of tweaking, accomplished everything that we felt was important to implement in such a worthy web app.

#### Wanna know a bit more? These include:

- Security.
  - A big focus was emphasized on masking the API token. We learned the best token security practices to keep those legal documents safe and sound.
- The Environment
  - Setting up the environment from the domain to hosting the server and networking was a task.
    - We learned more about A, AAAA, CNAME, and other records in order to maintain and protect our domain as much as possible.
- Design Philosophy
  - Held a group vote. We maintained a respectful manner with each other and collaborated well with each other, each of us playing a special role on the team.

## And the result...?

Nothing much to be said. We're proud of everything that we collaborated on together. We're proud of ourselves and our project. Furthermore, we've learned and grew to new heights. That in itself is possibly the greatest accomplishment of all.

Thank you, Knighthacks 2023!

## What's next for ReadMyLegal.

We'll be developing more accessibility features in the future while providing support for more file types such as .doc, .docx. We will also continue to brush up on our software development knowledge and continue to advocate for the growth of legal awareness.

## Let's set this up!

Here are instructions to run the server software backend and frontend.

Before you start the Java server, you must have `assemblyai-token.txt` and `openai-token.txt` in your working directory, the former containing an API token for AssemblyAI, the latter containing an API token for OpenAI.

Create and execute Java 17 Jar file

```
cd <repository directory>
cd backend-java
./gradlew shadowJar
java -jar ./app/build/libs/rml-backend.jar 5252 <password>
```

Initialize web interface with npm

```
cd <repository directory>
cd readmylegal
npm install
npm start # Will start on port 3000
```

## License information

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for details.
