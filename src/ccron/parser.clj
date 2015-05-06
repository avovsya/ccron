(ns ccron.parser)

(def cron-record { :min convert-time-specifier
                :hour convert-time-specifier
                :day-of-month convert-time-specifier
                :month convert-time-specifier
                :day-of-week convert-time-specifier
                :command identity })

(defn- convert-time-specifier [s]
  (when s
    (if (= s "*")
      nil
      (Integer. s))))

(defn- splitted-line->cron-record [line]
  (zipmap (keys cron-record) (rest (first line))))

(defn- apply-conversions [[key val]]
  [key ((key cron-record) val)])

(defn parse-line [line]
  (if-let [splitted-line (re-seq #"([\d*])\s([\d*])\s([\d*])\s([\d*])\s([\d*])\s*(.*)" line) ]
    (into {} (map apply-conversions (splitted-line->cron-record splitted-line)))
    nil))
