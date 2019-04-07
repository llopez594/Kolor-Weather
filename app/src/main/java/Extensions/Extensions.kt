package Extensions

import org.json.JSONArray
import org.json.JSONObject

operator fun JSONArray.iterator(): Iterator<JSONObject>
        = (0 until this.length()).asSequence().map { this.get(it) as JSONObject }.iterator()