import React, { useEffect, useState } from 'react';

function App() {
  const [emps, setEmps] = useState([]);

  useEffect(() => {
    fetch('/api/employees/active')
        .then(r=>r.json())
        .then(setEmps)
        .catch(console.error);
  }, []);

  return (
      <div className="min-h-screen bg-gray-100">
        <header className="bg-white shadow">
          <div className="max-w-4xl mx-auto p-6">
            <h1 className="text-3xl font-bold">Active Employees</h1>
          </div>
        </header>
        <main className="max-w-4xl mx-auto p-6 grid sm:grid-cols-2 lg:grid-cols-3 gap-6">
          {emps.map(e=>(
              <div key={e.id} className="bg-white rounded-lg shadow p-5">
                <h2 className="text-xl font-semibold">{e.name}</h2>
                <p className="text-gray-600">{e.position}</p>
                <p className="mt-2 text-sm text-gray-500">
                  Hired: {e.hireDate}
                </p>
              </div>
          ))}
        </main>
      </div>
  );
}

export default App;