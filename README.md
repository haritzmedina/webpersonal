Some web application java api examples:
 * [Flickr API](https://www.flickr.com/services/api/) api using rest calls.
 * [Delicious](http://delicious.com/) old v1 api using rest calls with HTTP authentication.
 * Twitter OAuth authentication through [Twitter4j](http://twitter4j.org/) library.
 * Yahoo OAuth example

It is included a mashup which finds uploaded photos with a special tag and it shares the photo through delicious and twitter.
You can find under [Mashup.java](src/main/java/com/haritzmedina/sia/webpersonal/Mashup.java)
To run it is necessary to rename and config (fill) [some properties files](properties/):
 * delicious.template.properties -> delicious.properties
 * flickr.template.properties -> flickr.properties
 * twitter.template.properties -> twitter.properties