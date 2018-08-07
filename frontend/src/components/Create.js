import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import { Link } from 'react-router-dom';

class Create extends Component {

  constructor() {
    super();
    this.state = {
      name: '',
      type: '',
      category: '',
      keywords: '',
      message: ''
    };
  }
  onChange = (e) => {
    const state = this.state
    state[e.target.name] = e.target.value;
    this.setState(state);
  }

  onSubmit = (e) => {
    e.preventDefault();

    const { name, type, category, keywords, message } = this.state;

    axios.post('/message/record', { name, type, category, keywords, message })
      .then((result) => {
        this.props.history.push("/")
      });
  }

  render() {
    const { name, type, category, keywords, message } = this.state;
    return (
      <div class="container">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">
              ADD MESSAGE
            </h3>
          </div>
          <div class="panel-body">
            <h4><Link to="/"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Messages List</Link></h4>
            <form onSubmit={this.onSubmit}>
              <div class="form-group">
                <label for="isbn">Name:</label>
                <input type="text" class="form-control" name="name" value={name} onChange={this.onChange} placeholder="Name" />
              </div>
              <div class="form-group">
                <label for="title">Type:</label>
                <input type="text" class="form-control" name="type" value={type} onChange={this.onChange} placeholder="Type" />
              </div>
              <div class="form-group">
                <label for="author">Category:</label>
                <input type="text" class="form-control" name="category" value={category} onChange={this.onChange} placeholder="Category" />
              </div>
              <div class="form-group">
                <label for="published_date">Keywords:</label>
                <input type="text" class="form-control" name="keywords" value={keywords} onChange={this.onChange} placeholder="keywords" />
              </div>
              <div class="form-group">
                <label for="publisher">Message:</label>
                <textarea rows="4" cols="50" class="form-control" name="message" value={message} onChange={this.onChange} >
                add your message here
                </textarea>
              </div>
              <button type="submit" class="btn btn-default">Submit</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default Create;
