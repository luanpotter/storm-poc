const _ = require('lodash');

const fact = n => n < 2 ? 1 : n * fact(n - 1);
const isPrime = n => n <= 2 ? n == 2 : !_.range(2, Math.ceil(Math.sqrt(n) + 1)).some(i => n % i === 0);

const max = 10;
const sum = _.range(max).map(n => fact(n) + n + 1).filter(isPrime).reduce((a, b) => a + b);
console.log(sum);
