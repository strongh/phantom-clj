(ns example
  (:require-macros [phantom-clj.webpage :as ph]))

(let [address "https://www.google.com"
      output "screenshot.png"
      page (ph/create-webpage)]
  (ph/open-webpage
   page address
   (fn [status]
     (if-not (= "success" status)
       (ph/log status)
       (do
	 (ph/webpage-eval
	  page
	  (fn []
	    (ph/set-query-value!
	     "input[name=q]" "gangnam style")))
	 (ph/with-window-timeout
	   (fn []
	     (ph/render-webpage page output)
	     (ph/exit-phantom!))
	   4000))))))