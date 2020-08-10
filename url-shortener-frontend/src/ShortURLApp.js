import React, { Component } from 'react';
import axios from 'axios';

class ShortURLApp extends Component {

  constructor(props) {
    super(props);
    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.state = {
      shortURlGenerated: '',
      fullURL: '',
      done: null
    }
  }

  onChange(e) {
    this.setState({fullURL: e.target.value});
  }

  onSubmit(e) {
    e.preventDefault();

    const urlToSend = {
      fullURL: this.state.fullURL
    };

//add axios post and grab response url.
    axios.post('http://localhost:8080/api/shorturl/create', urlToSend)
      .then((res) => {
        console.log(res.data)
        this.setState({
          shortURlGenerated: res.data.shortSlug,
          done: true
        })
        console.log(this.state.shortURlGenerated)
      }).catch((error) => {
        console.log(error)
      });

  }

  render(){
    if (!this.state.done){
      return(
        <form onSubmit={this.onSubmit}>
        <div className="form-group" id="mainapp">
          <label>Enter Url to Shorten</label>
          <input type="text" value={this.state.fullURL} onChange={this.onChange} className="form-control" />
        </div>
        <div className="form-group">
          <input type="submit" value="Create Short URL" className="btn btn-success btn-block" />
        </div>
      </form>
      )
    } else {
      return (
        <h1>Short URL: <a href={"http://localhost:3000/r/" + this.state.shortURlGenerated}>{"http://localhost:3000/r/" + this.state.shortURlGenerated}</a></h1>
      )
    }
  }
}

export default ShortURLApp
