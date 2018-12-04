const ip = require("ip");
const fs = require("fs");

const address = ip.address();
fs.writeFile("logs/ip.log", address, err => {
  if (err) {
    console.log(err);
  } else {
    console.log(`Saved address: ${address}`);
  }
});
