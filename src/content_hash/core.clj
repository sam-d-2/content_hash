(ns content-hash.core
  (:require [clojure.java.io :as io])
  (:gen-class))


(defn file-to-byte-array
  [^java.io.File file]
  (let [result (byte-array (.length file))]
    (with-open [in (java.io.DataInputStream. (io/input-stream file))]
      (.readFully in result))
    result))

(defn file-to-bits [file] (vec (file-to-byte-array (io/file file))))
(defn byte-to-bits [b] (reverse (map (fn [i] (bit-and 1 (bit-shift-right b i))) (range 0 8))))
(defn file-bits [file] (->> file (file-to-bits) (map byte-to-bits) (flatten)))
(defn md5-n-bits-to-pad [bits] (let [to-pad (mod (- 448 (count bits)) 512)]
                            (if (= 0 to-pad) 512 to-pad)))
(defn pad-bit-and-zeroes [n]
  "This shouldn't be zero"
  (conj (repeat (dec n) 0) 1))

(defn md5-pad [bits]
  (let [n (md5-n-bits-to-pad bits)]
    (concat bits (pad-bit-and-zeroes n))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
