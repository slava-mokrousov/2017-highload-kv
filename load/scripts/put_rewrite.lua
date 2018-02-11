wrk.method = "PUT"
counter = 0
repeatNum = 749

request = function()
   path = "/v0/entity?id=" .. counter
   value = ""
   for i = 1, 4200 do
      value = value .. string.char(math.random(65, 122))
   end
   wrk.body = value
   counter = (counter + 1) % repeatNum
   return wrk.format(nil, path)
end