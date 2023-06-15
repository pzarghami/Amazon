import axios from "axios";
import { getAuthToken } from "./getAuthToken";

export default class Request {
  static async get(url, header, options) {
    header = header || { ...getAuthToken() };
    const response = await axios.get(url, {
      headers: header,
    })
    return response;
  }

  static async post(url, data, header, options) {
    header = header || { ...getAuthToken() };

    const response = await axios.post(url, data, {
      headers: header
    });
    return response;
  }

  static async delete(url, header, options) {
    header = header || { ...getAuthToken() };
    const response = await axios.delete(url, {
      headers: header,
    })
    return response;
  }
}