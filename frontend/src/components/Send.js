import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

class Send extends Component {

  constructor(props) {
    super(props);
    this.state = {
      message: {}
    };
  }

  componentDidMount() {
    var init = { "name" : this.props.match.params.id, "toUser": "", "parameters": "HEADER_1===感谢您预订酒店&&&HOTEL_NAME===Best Oriental Hotel&&&ROOM_NAME===Luxury Room&&&HOTEL_PRICE===212&&&BOOKING_ID===5578999&&&CUSTOMER_SERVICE_PHONE===13717636288"};
    this.setState({ message: init });
  }

  onChange = (e) => {
    const state = this.state.message
    state[e.target.name] = e.target.value;
    this.setState({message:state});
  }

  onSubmit = (e) => {
    e.preventDefault();

    const { name, toUser, parameters } = this.state.message;

    axios.post('/message/send', { name, toUser, parameters})
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
              SEND MESSAGE
            </h3>
          </div>
          <div class="panel-body">
            <h4><Link to="/"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Message List</Link></h4>
            <form onSubmit={this.onSubmit}>
              <div class="form-group">
                <label for="name">name:</label>
                <input readonly="readonly" type="text" class="form-control" name="name" value={this.state.message.name} onChange={this.onChange}  />
              </div>
              <div class="form-group">
                <label for="title">toUser:</label>
                <input type="text" class="form-control" name="toUser" value={this.state.message.toUser} onChange={this.onChange}  />
              </div>
              <div class="form-group">
                <label for="author">parameters:</label>
                <input type="text" class="form-control" name="parameters" value={this.state.message.parameters} onChange={this.onChange}  />
              </div>

              <button type="submit" class="btn btn-info">Send</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

export default Send;
