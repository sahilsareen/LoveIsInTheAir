# LoveIsInTheAir

A spark based twitter streaming application that was used to process valentines tweets from all over India during the Valentines week of 2016 for analyzing the cities and states of India in love!

This app live streams tweets from twitter and filters tweets from India, then determines the city from each tweet and accumulates city names and corresponding tweet counts for all the cities found. These tuples of city names and corresponding counts are stored in a text file for further analysis.

[`LoveIsInTheAir-Stats-Plotter`](https://github.com/sahilsareen/LoveIsInTheAir-Stats-Plotter) is used to map each city to a state in India and plot a pie chart distribution of tweets for all Indian states.

# Spark streaming UI
<img src="http://i.imgur.com/lLyoYmP.jpg">

# Results
- [27.20 Million](https://github.com/sahilsareen/LoveIsInTheAir/blob/master/analysis/LoveIsInTheAir%20-%20Valentines%20Streaming%20Statistics.pdf) tweets from India processed during the valentines week of 2016.
- State wise distribution of tweets:
[<img src="http://i.imgur.com/q1ZV1Gt.png" />](https://plot.ly/~sahilsareen/10.embed)
- Upon normalization of tweets by area of state, Delhi scores [89.704192](https://github.com/sahilsareen/LoveIsInTheAir/blob/master/analysis/Area%20Normalized%20Tweets.xlsx)
- Only 0.0418588% of tweets from India were with a location embedded

# Setup and HowTo

1. Install [`sbt`](http://www.scala-sbt.org/download.html)

2. Clone LoveIsInTheAir: `git clone https://github.com/sahilsareen/LoveIsInTheAir.git`

3. Create a new [twitter app](https://apps.twitter.com/) and generate an access token.

4. Run `cd LoveIsInTheAir && sbt package run <consumer key> <consumer secret> <access token> <access token secret> [<twitter love filters>]`

5. After collecting sufficient data, use [LoveIsInTheAir-Stats-Plotter](https://github.com/sahilsareen/LoveIsInTheAir-Stats-Plotter) to visualize results.

# Contributing

1. Generate a pull request, OR
2. Email patches to `sahil [DOT] sareen [AT] hotmail [DOT] com`

* Stick to the [scala style guide](https://github.com/databricks/scala-style-guide)

# License

See [License](https://github.com/sahilsareen/LoveIsInTheAir/blob/master/LICENSE)

# Author

- Sahil Sareen (sahil [DOT] sareen [AT] hotmail [DOT] com)
