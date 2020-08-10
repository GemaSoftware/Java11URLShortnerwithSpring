import React, { Component } from 'react';
import axios from 'axios';

class RedirectToURL extends Component {

    constructor(props) {
        super(props);
        this.tryRedirect = this.tryRedirect.bind(this);
        this.state = {
            redirectURL: this.props.match.params.slug,
            done: null,
            message: 'Could not redirect to URL'
        }
    }



    tryRedirect() {
        axios.get("http://localhost:8080/api/shorturl/" + this.state.redirectURL)
            .then(resp => {
                console.log(resp.data)
                if(!resp.data.fullURL){
                    this.setState({
                        message: 'Short URL not found.',
                        done: true
                    })
                } else {
                    window.location = resp.data.fullURL
                }
            });
    }

    render(){
        if(!this.state.done){
            this.tryRedirect();
            return(
                <h1>Redirecting...</h1>
            )
        } else {
            return(
                <h1>{this.state.message}</h1>
            )
        }

    }


}

export default RedirectToURL
