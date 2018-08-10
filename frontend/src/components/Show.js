import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

class Show extends Component {

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

  delete(id){
    console.log(id);
    axios.delete('/message/record/'+id)
      .then((result) => {
        this.props.history.push("/")
      });
  }

  render() {
    return (
      <div class="container">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">
              MESSAGE DETAILS
            </h3>
          </div>
          <div class="panel-body">
            <h4><Link to="/"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Message List</Link></h4>
            <dl>
              <dt>name:</dt>
              <dd>{this.state.message.name}</dd>
              <dt>type:</dt>
              <dd>{this.state.message.type}</dd>
              <dt>category:</dt>
              <dd>{this.state.message.category}</dd>
              <dt>last modified date:</dt>
              <dd>{this.state.message.createdDate}</dd>
              <dt>keywords:</dt>
              <dd>{this.state.message.keywords}</dd>
              <dt>placeholders:</dt>
              <dd>{this.state.message.placeholders}</dd>
              <dt>message:</dt>
              <dd>{this.state.message.message}</dd>
            </dl>
            <Link to={`/edit/${this.state.message._id}`} class="btn btn-success">Edit</Link>&nbsp;&nbsp;
             <Link to={`/send/${this.state.message.name}`} class="btn btn-info">Send</Link>&nbsp;&nbsp;
            <button onClick={this.delete.bind(this, this.state.message._id)} class="btn btn-danger">Delete</button>

          </div>
        </div>
      </div>
    );
  }
}

export default Show;
