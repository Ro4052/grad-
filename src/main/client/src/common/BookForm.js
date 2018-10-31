import React, { Component } from 'react'

export default class BookForm extends Component {
    render() {
        const { state } = this.props
        let date = new Date().getFullYear();
        return (
            <div>
                <div>
                    <label>ISBN</label>
                    <input type="text" pattern="^[0-9]{10}|[0-9]{13}$" maxLength="13"
                        value={state.isbn} id="isbn" onChange={this.props.handleChange} ></input>
                </div>
                <div>
                    <label>Title</label>
                    <input type="text" placeholder="Title"
                        value={state.title} id="title" maxLength="200" required onChange={this.props.handleChange}></input>
                </div>
                <div>
                    <label>Author</label>
                    <input type="text" placeholder="Author"
                        value={state.author} id="author" maxLength="200" required onChange={this.props.handleChange}></input>
                </div>
                <div>
                    <label>Publish Date</label>
                    <input type="number" placeholder="Publish Date"
                        value={state.publishDate} id="publishDate" pattern="^[0-9]{4}$" maxLength="4" max={date} onChange={this.props.handleChange}></input>
                </div>
                <div>
                    <button type="submit">Save Changes</button>
                </div>
            </div>
        )
    }
}
