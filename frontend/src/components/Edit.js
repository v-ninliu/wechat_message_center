import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

class Edit extends Component {

  constructor(props) {
    super(props);
    this.state = {
      message: {}
    };
  }

  componentDidMount() {
    axios.get('/message/record/'+this.props.match.params.id)
      .then(res => {
        this.setState({ message: res.data });
        console.log(this.state.message);
      });
  }

  onChange = (e) => {
    const state = this.state.message
    state[e.target.name] = e.target.value;
    this.setState({message:state});
  }

  onSubmit = (e) => {
    e.preventDefault();

    const { name, type, category, keywords, placeholders, message } = this.state.message;

    axios.put('/message/record/'+this.props.match.params.id, { name, type, category, keywords, placeholders, message })
      .then((result) => {
        this.props.history.push("/show/"+this.props.match.params.id)
      });
  }

  render() {
    return (
      <div class="container">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">
              EDIT message
            </h3>
          </div>
          <div class="panel-body">
            <h4><Link to="/"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Message List</Link></h4>
            <form onSubmit={this.onSubmit}>
              <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" name="name" value={this.state.message.name} onChange={this.onChange} />
              </div>
              <div class="form-group">
                <label for="title">type:</label>
                <input type="text" class="form-control" name="type" value={this.state.message.type} onChange={this.onChange}  />
              </div>
              <div class="form-group">
                <label for="author">category:</label>
                <input type="text" class="form-control" name="category" value={this.state.message.category} onChange={this.onChange} placeholder="category" />
              </div>
              <div class="form-group">
                <label for="published_date">keywords:</label>
                <input type="text" class="form-control" name="keywords" value={this.state.message.keywords} onChange={this.onChange} placeholder="keywords" />
              </div>
              <div class="form-group">
                <label for="published_date">placeholders:</label>
                <input type="text" class="form-control" name="placeholders" value={this.state.message.placeholders} onChange={this.onChange} placeholder="placeholders" />
              </div>
              <div class="form-group">
              <label>message:</label>
              <textarea rows="4" cols="50" class="form-control" name="message" value={this.state.message.message} onChange={this.onChange} >
                  {this.state.message.message}
              </textarea>
              </div>
              <button type="submit" class="btn btn-default">Update</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default Edit;
