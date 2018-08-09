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
      placeholders: '',
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

    const { name, type, category, keywords, placeholders, message } = this.state;

    axios.post('/message/record', { name, type, category, keywords, placeholders, message })
      .then((result) => {
        this.props.history.push("/")
      });
  }

  render() {
    const { name, type, category, keywords, placeholders, message } = this.state;
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
                <label>name:</label>
                <input type="text" class="form-control" name="name" value={name} onChange={this.onChange} placeholder="name" />
              </div>
              <div class="form-group">
                <label>type:</label>
                <input type="text" class="form-control" name="type" value={type} onChange={this.onChange} placeholder="type" />
              </div>
              <div class="form-group">
                <label>category:</label>
                <input type="text" class="form-control" name="category" value={category} onChange={this.onChange} placeholder="category" />
              </div>
              <div class="form-group">
                <label>keywords:</label>
                <input type="text" class="form-control" name="keywords" value={keywords} onChange={this.onChange} placeholder="keywords" />
              </div>
               <div class="form-group">
                 <label>placeholders:</label>
                 <input type="text" class="form-control" name="placeholders" value={placeholders} onChange={this.onChange} placeholder="placeholders" />
              </div>
              <div class="form-group">
                <label>message:</label>
                <textarea rows="4" cols="50" class="form-control" name="message" value={message} onChange={this.onChange} placeholder="message">
                add your message here
                </textarea>
              </div>
              <button type="submit" class="btn btn-success">Submit</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default Create;
