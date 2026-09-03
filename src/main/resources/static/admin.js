const ADMIN_KEY = new URLSearchParams(window.location.search).get("key");

function loadBookings() {
    fetch(`/admin/bookings?key=${ADMIN_KEY}`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector('#bookingTable tbody');
            tbody.innerHTML = '';

            data.forEach(booking => {
                const row = document.createElement('tr');

                row.innerHTML = `
                    <td>${booking.id}</td>
                    <td>${booking.customerName}</td>
                    <td>${booking.customerEmail}</td>
                    <td>${booking.type}</td>
                    <td>${booking.quantity}</td>
                    <td>${booking.createdAt}</td>
                `;

                tbody.appendChild(row);
            });
        })
        .catch(err => {
            alert("Kunde inte hämta bokningar");
            console.error(err);
        });
}

loadBookings();
