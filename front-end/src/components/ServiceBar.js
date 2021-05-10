import { getByDisplayValue } from '@testing-library/dom';
import React, { useEffect, useState } from 'react';
import "../stylesheets/styles.css"


const ServiceBar = () => {

    const [ shortened, setShortened ] = useState('');
    const [ url, setURL ] = useState('');

    const shortenURL = (event) => {

        fetch('http://localhost:8000/url', {
            crossDomain: true,
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: url
        }).then(res => {
            console.log(res);
            if(res.status == 200 || res.status == 204) {
                return res.text();
            } 
            return "Please enter in a valid URL. ";
        })
          .then((data) => {
            if(data == "Please enter in a valid URL. ") {
                console.log("here2");
                setShortened("Please enter in a valid URL. ")
                return;
            }
            let shortenedURL = "http://localhost:3000/url/" + data;
            setShortened(shortenedURL);
          })
    }

    return (
        <div>
            <div className="url-input">
                <h1> URL Shortener </h1> 
                <div class="info">
                    <p>Input a valid URL below and our service will return a shortened URL. To access the original URL, just paste the shortened
                        URL into your browser's URL bar.
                    </p>
                </div>
                <div className="form-group">
                    <input type="text" className="form-control" id="url" placeholder="Enter URL here"  value={url} onChange={(e) => setURL(e.target.value)}/>
                </div>
                <button  className="btn" onClick={(e) => shortenURL(url)}>Submit</button>
            </div>

            <div className="display-url-div">
                <p id="shortenedURL">{ shortened }</p>
            </div>

            
        </div>

    );
}

export default ServiceBar;