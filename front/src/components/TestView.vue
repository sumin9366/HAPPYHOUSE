<template>
  <div class="container mt-5">
    <table class="table table-borderless table-responsive card-1 p-4">
      <thead>
        <tr class="border-bottom">
          <th>
            <span class="ml-2">Time</span>
          </th>
          <th>
            <span class="ml-2">Agent</span>
          </th>
          <th>
            <span class="ml-2">Customer</span>
          </th>
          <th>
            <span class="ml-2">Location</span>
          </th>
          <th>
            <span class="ml-4">Action</span>
          </th>
        </tr>
      </thead>
      <tbody class="cursor-pointer">
        <tr class="border-bottom">
          <td>
            <div class="p-2">
              <span class="d-block font-weight-bold">Tomorrow</span>
              <small>2:30PM</small>
            </div>
          </td>
          <td>
            <div class="p-2 d-flex flex-row align-items-center mb-2">
              <div class="d-flex flex-column ml-2">
                <span class="d-block font-weight-bold">Jennifer john</span>
                <small class="text-muted">Jasmine Owner Reality group</small>
              </div>
            </div>
          </td>
          <td>
            <div class="p-2">
              <span class="font-weight-bold">Ammy Song</span>
            </div>
          </td>
          <td>
            <div class="p-2 d-flex flex-column">
              <span>1 City point,#2A</span>
              <span> Brooklyn,NY</span>
            </div>
          </td>
          <td>
            <div class="p-2 icons">
              <i class="fa fa-phone text-danger"></i>
              <i class="fa fa-adjust text-danger"></i>
              <i class="fa fa-share"></i>
            </div>
          </td>
        </tr>

        <tr class="border-bottom">
          <td>
            <div class="p-2">
              <span class="d-block font-weight-bold">Tomorrow</span>
              <small>3:30PM</small>
            </div>
          </td>
          <td>
            <div class="p-2 d-flex flex-row align-items-center mb-2">
              <div class="d-flex flex-column ml-2">
                <span class="d-block font-weight-bold">David Smith</span>
                <small class="text-muted">Jasmine Owner Reality group</small>
              </div>
            </div>
          </td>
          <td>
            <div class="p-2">
              <span class="font-weight-bold">David Clark</span>
            </div>
          </td>
          <td>
            <div class="p-2 d-flex flex-column">
              <span>205 2ndst,#2A,</span>
              <span> Brooklyn,NY</span>
            </div>
          </td>
          <td>
            <div class="p-2 icons">
              <i class="fa fa-phone text-danger"></i>
              <i class="fa fa-adjust text-danger"></i>
              <i class="fa fa-share"></i>
            </div>
          </td>
        </tr>

        <tr class="border-bottom">
          <td>
            <div class="p-2">
              <span class="d-block font-weight-bold">Tomorrow</span>
              <small>12:30PM</small>
            </div>
          </td>
          <td>
            <div class="p-2 d-flex flex-row align-items-center mb-2">
              <div class="d-flex flex-column ml-2">
                <span class="d-block font-weight-bold">Emmily johnson</span>
                <small class="text-muted">Jasmine Owner Reality group</small>
              </div>
            </div>
          </td>
          <td>
            <div class="p-2">
              <span class="font-weight-bold">Mary Kingston</span>
            </div>
          </td>
          <td>
            <div class="p-2 d-flex flex-column">
              <span>199 Bowery,#7A</span>
              <span> Brooklyn,NY</span>
            </div>
          </td>
          <td>
            <div class="p-2 icons">
              <i class="fa fa-phone text-danger"></i>
              <i class="fa fa-adjust text-danger"></i>
              <i class="fa fa-share"></i>
            </div>
          </td>
        </tr>

        <tr class="border-bottom">
          <td>
            <div class="p-2">
              <span class="d-block font-weight-bold">Tomorrow</span>
              <small>1:30PM</small>
            </div>
          </td>
          <td>
            <div class="p-2 d-flex flex-row align-items-center mb-2">
              <div class="d-flex flex-column ml-2">
                <span class="d-block font-weight-bold">Nick Jones</span>
                <small class="text-muted">Jasmine Owner Reality group</small>
              </div>
            </div>
          </td>
          <td>
            <div class="p-2">
              <span class="font-weight-bold">James Smith</span>
            </div>
          </td>
          <td>
            <div class="p-2 d-flex flex-column">
              <span>123 Clinton Ave,#2A</span>
              <span> Brooklyn,NY</span>
            </div>
          </td>
          <td>
            <div class="p-2 icons">
              <i class="fa fa-phone text-danger"></i>
              <i class="fa fa-adjust text-danger"></i>
              <i class="fa fa-share"></i>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.card-1 {
  border: none;
  border-radius: 10px;
  width: 100%;
  background-color: #fff;
}

.icons i {
  margin-left: 20px;
}

.cursor-pointer {
  cursor: pointer;
}

tbody tr:hover {
  background-color: #cff5cf;
}
</style>
