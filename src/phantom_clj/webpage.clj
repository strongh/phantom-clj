(ns phantom-clj.webpage)

;; These are all macros so that I didn't need to
;; worry about Phantom's sandboxing of page JS contexts.
;; This way fns can be used without regard to the
;; JS requirements in every new environment.

;; Generally useful utilities
(defmacro log [msg]
  `(.log js/console ~msg))

(defmacro set-query-value! [query value]
  `(set! (.-value (.querySelector js/document ~query))
	 ~value))

(defmacro click-query! [query]
  `(.click
    (.querySelector js/document ~query)))

(defmacro with-window-timeout [fn timeout]
  `(.setTimeout
    js/window ~fn ~timeout))

;; Webpage methods
(defmacro create-webpage []
  `(.create
    (js/require "webpage")))

(defmacro open-webpage [page address callback]
  `(.open ~page ~address ~callback))

(defmacro render-webpage [page output]
  `(.render ~page ~output))

(defmacro webpage-eval [page fn]
  `(.evaluate ~page ~fn))

;; Other important phantom stuff
(defmacro exit-phantom! []
  `(.exit js/phantom))