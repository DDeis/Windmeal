use windmeal
db.providers.ensureIndex( { "address.location": "2d" })
db.addUser({user:"windmeal",pwd:"esiea",roles:["readWrite"]})