import React, { useEffect } from 'react';


const Redirect = ({ match }) => {

    const {
        params: { urlID },
    } = match;

    useEffect(() => {

        fetch(`http://localhost:8000/url/${urlID}`)
            .then(res => res.text())
            .then((data) => {
                window.location.href = data;
            })
    }, []);

    return (
        <div>

        </div>
    );
}

export default Redirect;