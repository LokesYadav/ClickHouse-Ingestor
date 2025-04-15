import React, { useState } from 'react';

function App() {
  const [source, setSource] = useState('ClickHouse');
  const [clickhouseConfig, setClickhouseConfig] = useState({
    host: '',
    port: '',
    database: '',
    user: '',
    jwtToken: '',
  });
  const [flatFileConfig, setFlatFileConfig] = useState({
    fileName: '',
    delimiter: ',',
  });
  const [columns, setColumns] = useState([]);
  const [selectedColumns, setSelectedColumns] = useState([]);
  const [status, setStatus] = useState('');
  const [result, setResult] = useState('');

  const handleSourceChange = (e) => {
    setSource(e.target.value);
    setColumns([]);
    setSelectedColumns([]);
    setStatus('');
    setResult('');
  };

  const handleClickhouseConfigChange = (e) => {
    setClickhouseConfig({
      ...clickhouseConfig,
      [e.target.name]: e.target.value,
    });
  };

  const handleFlatFileConfigChange = (e) => {
    setFlatFileConfig({
      ...flatFileConfig,
      [e.target.name]: e.target.value,
    });
  };

  const handleLoadColumns = async () => {
    setStatus('Loading columns...');
    setResult('');
    try {
      if (source === 'ClickHouse') {
        // Fetch tables first (optional, can be used to select table)
        // For simplicity, assume user inputs table name in database field or elsewhere
        // Here, we fetch columns for a hardcoded table or database field
        const table = 'uk_price_paid'; // TODO: make selectable in UI
        const response = await fetch('/api/clickhouse/columns', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            host: clickhouseConfig.host,
            port: clickhouseConfig.port,
            database: clickhouseConfig.database,
            user: clickhouseConfig.user,
            jwtToken: clickhouseConfig.jwtToken,
            table: table,
          }),
        });
        if (!response.ok) throw new Error('Failed to fetch columns');
        const cols = await response.json();
        setColumns(cols);
      } else {
        const response = await fetch('/api/flatfile/columns', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            fileName: flatFileConfig.fileName,
            delimiter: flatFileConfig.delimiter,
          }),
        });
        if (!response.ok) throw new Error('Failed to fetch columns');
        const cols = await response.json();
        setColumns(cols);
      }
      setSelectedColumns([]);
      setStatus('Columns loaded.');
    } catch (error) {
      setStatus('Error loading columns: ' + error.message);
    }
  };

  const handleColumnSelection = (e) => {
    const col = e.target.value;
    if (e.target.checked) {
      setSelectedColumns([...selectedColumns, col]);
    } else {
      setSelectedColumns(selectedColumns.filter((c) => c !== col));
    }
  };

  const handleStartIngestion = async () => {
    setStatus('Starting ingestion...');
    setResult('');
    try {
      const response = await fetch('/api/ingest', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          source: source,
          target: source === 'ClickHouse' ? 'FlatFile' : 'ClickHouse',
          clickHouseConfig: clickhouseConfig,
          flatFileConfig: flatFileConfig,
          selectedColumns: selectedColumns,
        }),
      });
      if (!response.ok) throw new Error('Ingestion failed');
      const resultText = await response.text();
      setStatus('Ingestion completed.');
      setResult(resultText);
    } catch (error) {
      setStatus('Error during ingestion: ' + error.message);
    }
  };

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
      <h1>ClickHouse & Flat File Data Ingestion Tool</h1>

      <div>
        <label>
          Select Source:
          <select value={source} onChange={handleSourceChange}>
            <option value="ClickHouse">ClickHouse</option>
            <option value="FlatFile">Flat File</option>
          </select>
        </label>
      </div>

      {source === 'ClickHouse' ? (
        <div style={{ marginTop: '10px' }}>
          <h3>ClickHouse Configuration</h3>
          <label>
            Host:
            <input
              type="text"
              name="host"
              value={clickhouseConfig.host}
              onChange={handleClickhouseConfigChange}
            />
          </label>
          <br />
          <label>
            Port:
            <input
              type="text"
              name="port"
              value={clickhouseConfig.port}
              onChange={handleClickhouseConfigChange}
            />
          </label>
          <br />
          <label>
            Database:
            <input
              type="text"
              name="database"
              value={clickhouseConfig.database}
              onChange={handleClickhouseConfigChange}
            />
          </label>
          <br />
          <label>
            User:
            <input
              type="text"
              name="user"
              value={clickhouseConfig.user}
              onChange={handleClickhouseConfigChange}
            />
          </label>
          <br />
          <label>
            JWT Token:
            <input
              type="text"
              name="jwtToken"
              value={clickhouseConfig.jwtToken}
              onChange={handleClickhouseConfigChange}
            />
          </label>
        </div>
      ) : (
        <div style={{ marginTop: '10px' }}>
          <h3>Flat File Configuration</h3>
          <label>
            File Name:
            <input
              type="text"
              name="fileName"
              value={flatFileConfig.fileName}
              onChange={handleFlatFileConfigChange}
            />
          </label>
          <br />
          <label>
            Delimiter:
            <input
              type="text"
              name="delimiter"
              value={flatFileConfig.delimiter}
              onChange={handleFlatFileConfigChange}
            />
          </label>
        </div>
      )}

      <div style={{ marginTop: '10px' }}>
        <button onClick={handleLoadColumns}>Load Columns</button>
      </div>

      {columns.length > 0 && (
        <div style={{ marginTop: '10px' }}>
          <h3>Select Columns</h3>
          {columns.map((col) => (
            <label key={col} style={{ display: 'block' }}>
              <input
                type="checkbox"
                value={col}
                checked={selectedColumns.includes(col)}
                onChange={handleColumnSelection}
              />
              {col}
            </label>
          ))}
        </div>
      )}

      <div style={{ marginTop: '10px' }}>
        <button onClick={handleStartIngestion} disabled={selectedColumns.length === 0}>
          Start Ingestion
        </button>
      </div>

      <div style={{ marginTop: '10px' }}>
        <strong>Status:</strong> {status}
      </div>

      <div style={{ marginTop: '10px' }}>
        <strong>Result:</strong> {result}
      </div>
    </div>
  );
}

export default App;
