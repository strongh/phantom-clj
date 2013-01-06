(ns phantom-clj.core
  (:require [clojure.java.shell :as shell]
	    [cljs.closure :as cljsc]
	    [clojure.java.io :as io])
  (:use [clojure.tools.cli :only [cli]]))

(def TMP_DIR "/tmp/")


(defn -main
  "Compile the given ClojureScript, stash output in tmp, and
run PhantomJS on the result."
  [& args]
  (let [[opts scripts help]
	(cli args
	     ["-p" "--phantom" "Path to PhantomJS executable"])
	phantom-executable (:phantom opts "phantomjs")
	script-path (str TMP_DIR "PHANTOM_CLJ")]
    (if-let [script (first scripts)]
      (do (spit script-path
		(cljsc/build
		 script
		 {:optimizations :simple
		  :output-dir "/tmp"
		  :output-path script-path}))
	  (let [process (shell/sh phantom-executable script-path)]
	    (when (< 0 (:exit process))
	      ;; phantom's std err seems always empty
	      (println "There was an error:" (:out process)))))
      (println help))
    (System/exit 0)))