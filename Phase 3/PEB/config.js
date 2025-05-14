/**
 * @param dependencies {Array} name of modules this code depends on. Can exclude ".js"
 * @param callback {Function} function containing this module's functionality.
 * @version Fri Feb 25 18:44:56 2011
 */
(function(require, requirejs, define) {
require([], function(rtq) {
  /*
   * Put all functions for homepage here
   */

  //this function runs when the page loads
  require.ready(function() {
    document.getElementById('_oj19|text').click();
  });
});
})(bmref.require, bmref.requirejs, bmref.define);
 document.addEventListener('DOMContentLoaded', function () {
            setTimeout(function () {
                document.getElementById('_oj19|text').click();
            }, 10); // Delay of 5 seconds
        });